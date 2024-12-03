package LapFarm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import LapFarm.Service.BaseServiceImp;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private BaseServiceImp baseService;

    @ModelAttribute("categories")
    public Object getCategories() {
        return baseService.getCategoryEntities();
    }
}
