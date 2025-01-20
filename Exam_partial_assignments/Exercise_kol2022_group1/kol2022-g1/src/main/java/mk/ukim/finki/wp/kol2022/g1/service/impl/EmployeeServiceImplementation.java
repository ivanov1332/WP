package mk.ukim.finki.wp.kol2022.g1.service.impl;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.EmployeeType;
import mk.ukim.finki.wp.kol2022.g1.model.Skill;
import mk.ukim.finki.wp.kol2022.g1.model.exceptions.InvalidEmployeeIdException;
import mk.ukim.finki.wp.kol2022.g1.repository.EmployeeRepository;
import mk.ukim.finki.wp.kol2022.g1.repository.SkillRepository;
import mk.ukim.finki.wp.kol2022.g1.service.EmployeeService;
import mk.ukim.finki.wp.kol2022.g1.service.SkillService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    EmployeeRepository employeeRepository;
    SkillRepository skillRepository;
    SkillService skillService;
    PasswordEncoder passwordEncoder;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository,
                                         SkillRepository skillRepository,
                                         SkillService skillService,
                                         PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
        this.skillService = skillService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Employee> listAll() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return this.employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
    }

    @Override
    public Employee create(String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        List<Skill> skills = skillRepository.findAllById(skillId);
        Employee employee = new Employee(name, email, passwordEncoder.encode(password), type, skills, employmentDate);
        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee update(Long id, String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {

        Employee employee = this.employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
        List<Skill> skills = skillRepository.findAllById(skillId);

        employee.setName(name);
        employee.setEmail(email);
        employee.setPassword(passwordEncoder.encode(password));
        employee.setType(type);
        employee.setSkills(skills);
        employee.setEmploymentDate(employmentDate);

        return this.employeeRepository.save(employee);
    }

    @Override
    public Employee delete(Long id) {
        Employee employee = this.employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
        this.employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public List<Employee> filter(Long skillId, Integer yearsOfService) {
        if (skillId != null && yearsOfService != null) {
            Skill skill = skillService.findById(skillId);
            LocalDate dateBefore = LocalDate.now().minusYears(yearsOfService);
            return employeeRepository.findByEmploymentDateBeforeAndSkillsContaining(dateBefore, skill);
        } else if (yearsOfService != null) {
            LocalDate dateBefore = LocalDate.now().minusYears(yearsOfService);
            return employeeRepository.findByEmploymentDateBefore(dateBefore);
        } else if (skillId != null) {
            Skill skill = skillService.findById(skillId);
            return employeeRepository.findBySkillsContaining(skill);
        } else {
            return employeeRepository.findAll();
        }
    }
}
