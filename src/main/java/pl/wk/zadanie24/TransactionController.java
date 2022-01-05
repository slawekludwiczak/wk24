package pl.wk.zadanie24;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Controller
public class TransactionController {

    private final TransactionDAO transactionDAO;

    public TransactionController(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/add")
    public String form(Model model){
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("transactionTypes", TransactionType.values());
        return "form";
    }

    @PostMapping("/save")
    public String save(Transaction transaction) {
        transactionDAO.save(transaction);
        return "redirect:/";
    }

    @RequestMapping("/remove")
    public String remove(){
        return "removal";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam(name = "index", required = false) int index) {
        Boolean deleted = transactionDAO.delete(index);
        if (deleted) {
            return "redirect:/";
        } else {
            return "no_id";
        }
    }

    @GetMapping("/id_to_edit")
    public String findTranactionToBeEditId() {
        return "edit_id";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam(name = "index", required = false) int index) {
        model.addAttribute("transactionTypes", TransactionType.values());
        Optional<Transaction> optionalTransaction = transactionDAO.findTransactionById(index);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            model.addAttribute("transaction", transaction);
            return "edit_form";
        } else {
            return "no_id";
        }
    }

    @RequestMapping("/update")
    public String update(Transaction transaction) {
        transactionDAO.update(transaction);
        return "redirect:/";
    }


    @GetMapping("/list")
    public String inbound(@RequestParam String type, Model model) {
        List<Transaction> transactions;
        if (type.equalsIgnoreCase("in")) {
            transactions = transactionDAO.findInboundTransactions();
            model.addAttribute("listName", "Przychody");
        } else {
            transactions = transactionDAO.findOutboundTransactions();
            model.addAttribute("listName", "Wydatki");
        }
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

}
