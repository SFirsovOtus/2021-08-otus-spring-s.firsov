package ru.otus.spring.credit.calculator.controller.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.credit.calculator.domain.Credit;
import ru.otus.spring.credit.calculator.domain.CreditParameters;
import ru.otus.spring.credit.calculator.domain.Schedule;
import ru.otus.spring.credit.calculator.domain.ScheduleChain;
import ru.otus.spring.credit.calculator.service.CreditService;
import ru.otus.spring.credit.calculator.service.calculation.ScheduleCalculationService;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.spring.credit.calculator.controller.ui.ScheduleController.URL_SCHEDULE_CHAIN;

@Controller
@RequiredArgsConstructor
public class CreditController {

    private final CreditService creditService;
    private final ScheduleCalculationService scheduleCalculationService;


    public static final String URL_CREDITS="/credits";
    public static final String URL_DELETE_CREDIT="/delete-credit";
    public static final String URL_ADD_CREDIT="/add-credit";
    public static final String URL_SAVE_CREDIT = "/save-credit";

    public static final String PARAM_CREDIT_ID="credit_id";


    @GetMapping(URL_CREDITS)
    public String showAllCredits(Model model, Principal principal) {
        List<Credit> credits = creditService.findAll(principal.getName()).stream()
                .sorted(Comparator.comparing(Credit::getScheduleChainId))
                .collect(Collectors.toList());

        model.addAttribute("credits", credits);
        model.addAttribute("url_delete_credit", URL_DELETE_CREDIT);
        model.addAttribute("param_credit_id", PARAM_CREDIT_ID);
        model.addAttribute("url_add_credit", URL_ADD_CREDIT);
        model.addAttribute("url_schedule_chain", URL_SCHEDULE_CHAIN);

        return "credits";
    }

    @PostMapping(URL_DELETE_CREDIT)
    public String deleteCredits(@RequestParam(PARAM_CREDIT_ID) String creditId) {
        creditService.deleteById(creditId);

        return "redirect:" + URL_CREDITS;
    }

    @GetMapping(URL_ADD_CREDIT)
    public String addCredits(Model model) {
        model.addAttribute("url_credits", URL_CREDITS);
        model.addAttribute("url_save_credit", URL_SAVE_CREDIT);

        return "add-credit";
    }

    @PostMapping(URL_SAVE_CREDIT)
    public String saveCredit(String amount, String percent, String month, String duration, Principal principal) {
        String[] yearAndMonth = month.split("-");
        int startYear = Integer.parseInt(yearAndMonth[0]);
        int startMonth = Integer.parseInt(yearAndMonth[1]);

        CreditParameters creditParameters = new CreditParameters(
                new BigDecimal(amount),
                new BigDecimal(percent),
                YearMonth.of(startYear, startMonth),
                Integer.parseInt(duration)
        );
        Schedule schedule = scheduleCalculationService.getOriginalSchedule(creditParameters);
        ScheduleChain scheduleChain = new ScheduleChain(principal.getName(), creditParameters, List.of(schedule));

        scheduleChain = creditService.save(scheduleChain);

        return "redirect:" + URL_SCHEDULE_CHAIN + "?" + PARAM_CREDIT_ID + "=" + scheduleChain.getId();
    }

}
