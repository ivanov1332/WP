package mk.ukim.finki.wp.kol2024g1.web;

import mk.ukim.finki.wp.kol2024g1.model.Hotel;
import mk.ukim.finki.wp.kol2024g1.model.Reservation;
import mk.ukim.finki.wp.kol2024g1.model.RoomType;
import mk.ukim.finki.wp.kol2024g1.service.HotelService;
import mk.ukim.finki.wp.kol2024g1.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ReservationsController {

    private final ReservationService reservationService;
    private final HotelService hotelService;

    public ReservationsController(ReservationService reservationService, HotelService hotelService) {
        this.reservationService = reservationService;
        this.hotelService = hotelService;
    }

    /**
     * This method should use the "list.html" template to display all reservations.
     * The method should be mapped on paths '/' and '/reservations'.
     * The arguments that this method takes are optional and can be 'null'.
     * In the case when the arguments are not passed (both are 'null') all reservations should be displayed.
     * If some, or all of the arguments are not 'null', the filtered reservations that are the result of the call
     * listAll method from the ReservationService should be displayed.
     * If you want to return a paginated result, you should also pass the page number and the page size as arguments.
     *
     * @param guestName
     * @param roomType
     * @param hotel
     * @param pageNum
     * @param pageSize
     * @return The view "list.html".
     */
    @GetMapping({"/", "/reservations"})
    public String listAll(@RequestParam(required = false) String guestName,
                          @RequestParam(required = false) RoomType roomType,
                          @RequestParam(required = false) Long hotel,
                          @RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          Model model) {
        Page<Reservation> page = reservationService.findPage(guestName, roomType, hotel, pageNum - 1, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("reservations", page.getContent());
        model.addAttribute("hotel", hotel);
        model.addAttribute("roomTypes", RoomType.values());
        model.addAttribute("hotels",hotelService.listAll());
        return "list";
    }

    /**
     * This method should display the "form.html" template.
     * The method should be mapped on path '/reservations/add'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/reservations/add")
    public String showAdd(Model model) {
//        List<Reservation> reservations = this.reservationService.listAll();
        List<Hotel> hotels = this.hotelService.listAll();
//        model.addAttribute("reservations", reservations);
        model.addAttribute("hotels", hotels);
        model.addAttribute("roomTypes", RoomType.values());
        return "form";
    }

    /**
     * This method should display the "form.html" template.
     * However, in this case, all 'input' elements should be filled with the appropriate value for the reservations that is updated.
     * The method should be mapped on path '/reservations/edit/[id]'.
     *
     * @return The view "form.html".
     */
    @GetMapping("/reservations/edit/{id}")
    public String showEdit(@PathVariable Long id,Model model) {
        Reservation reservation = reservationService.findById(id);
//        Hotel hotel = hotelService.findById(reservation.getHotel().getId());
        model.addAttribute("reservation", reservation);
//        model.addAttribute("hotel", hotel);
        model.addAttribute("roomTypes", RoomType.values());
        model.addAttribute("hotels",hotelService.listAll());
        return "form";
    }

    /**
     * This method should create a reservation given the arguments it takes.
     * The method should be mapped on path '/reservations'.
     * After the reservation is created, all reservations should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/reservations")
    public String create(@RequestParam (required = false) String guestName,
                         @RequestParam (required = false) LocalDate dateCreated,
                         @RequestParam (required = false) Integer daysOfStay,
                         @RequestParam (required = false) RoomType roomType,
                         @RequestParam (required = false) Long hotelId) {
        this.reservationService.create(guestName, dateCreated, daysOfStay, roomType, hotelId);
        return "redirect:/reservations";
    }

    /**
     * This method should update a reservation given the arguments it takes.
     * The method should be mapped on path '/reservations/[id]'.
     * After the reservation is updated, all reservations should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/reservations/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam(required = false) String guestName,
                         @RequestParam(required = false) LocalDate dateCreated,
                         @RequestParam(required = false) Integer daysOfStay,
                         @RequestParam(required = false) RoomType roomType,
                         @RequestParam(required = false) Long hotelId) {
        this.reservationService.update(id, guestName, dateCreated, daysOfStay, roomType, hotelId);
        return "redirect:/reservations";
    }

    /**
     * This method should delete the reservation that has the appropriate identifier.
     * The method should be mapped on path '/reservations/delete/[id]'.
     * After the reservation is deleted, all reservations should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/reservations/delete/{id}")
    public String delete(@PathVariable Long id) {
        this.reservationService.delete(id);
        return "redirect:/reservations";
    }

    /**
     * This method should implement the logic for extending the duration,
     * by adding one day to the daysOfStay.
     * The method should be mapped on path '/reservations/extend/[id]'.
     * After the operation, all reservations should be displayed.
     *
     * @return The view "list.html".
     */
    @PostMapping("/reservations/extend/{id}")
    public String extend(@PathVariable Long id) {
        this.reservationService.extendStay(id);
        return "redirect:/reservations";
    }
}
