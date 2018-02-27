//package poe.spring.api;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import poe.spring.delegate.LoginCreationDelegate;
//import poe.spring.domain.Voyage;
//import poe.spring.exception.DuplicateLoginBusinessException;
//import poe.spring.repository.VoyageRepository;
//import poe.spring.service.VoyageManagerService;
//
//@RestController
//@RequestMapping("/api/voyage")
//public class VoyageRestController {
//
//	@Autowired
//	VoyageManagerService voyageManagerService;
//
//	@PostMapping
//    public Voyage save(@RequestBody Voyage voyage) {
//        Voyage savedVoyage = null;
//        savedVoyage = voyageManagerService.signup(
//        		voyage.getVilleDepart(), 
//        		voyage.getVilleArrive(), 
//        		voyage.getDateDepart(), 
//        		voyage.getPrix(), 
//        		voyage.getNbPlace());
//        System.out.println("voyage id : " + savedVoyage);
//        return savedVoyage;
//    }
//
//	@GetMapping
//	public List<Voyage> listVoyages() {
//		List<Voyage> listVoyages = voyageManagerService.listVoyages();
//		System.out.println(listVoyages);
//		return listVoyages;
//	}
//
//	@RequestMapping("/id/{id}")
//	public Voyage getVoyageById(@PathVariable(value = "id") Long id) {
//		Voyage voyage = voyageManagerService.getVoyageById(id);
//		System.out.println(voyage);
//		return voyage;
//	}
//
//	@RequestMapping("/delete/id/{id}")
//	public void deleteVoyageById(@PathVariable(value = "id") Long id) {
//		Voyage voyage = voyageManagerService.getVoyageById(id);
//		System.out.println("Delete voyage : " + voyage);
//		voyageManagerService.deleteVoyage(voyage);
//	}
//
//	@PostMapping("/update/id/{id}")
//	public Voyage updateVoyageById(@PathVariable(value = "id") Long id, @RequestBody Voyage voyageJSon) {
//		Voyage voyage = voyageManagerService.getVoyageById(id);
//		System.out.println("Voyage before update : " + voyage);
//		voyageManagerService.updateVoyage(
//				voyage, 
//				voyageJSon.getVilleDepart(), 
//				voyageJSon.getVilleArrive(), 
//				voyageJSon.getDateDepart(), 
//				voyageJSon.getPrix(), 
//				voyageJSon.getNbPlace());
//		System.out.println("Voyage after update : " + voyage);
//		return voyage;
//	}
//
//}