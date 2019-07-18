package om.tricon.customer.eController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tricon.customer.model.CustomerES;
import com.tricon.customer.repository.customerElasticRepository;

@RestController
@RequestMapping("/v1")
public class ElasticController {
	@Autowired
	private customerElasticRepository customerElasticRepository;
	
	@GetMapping("/all")
	public Iterable<CustomerES> getAll(){
		List<CustomerES> ls =  (List<CustomerES>) customerElasticRepository.findAll();
		return customerElasticRepository.findAll();
	}
	
	@GetMapping("/test")
	public String test(){
		System.out.println("test");
		return "test";
	}

}
