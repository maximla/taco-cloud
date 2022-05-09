package tacos.web.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tacos.Taco;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path = "/apiDesign", produces = "application/json")
@CrossOrigin(origins = "*")
public class DesignTacoApiController {
    private TacoRepository tacoRepository;


    @Autowired
    public DesignTacoApiController(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

//    @GetMapping("/recent")
//    public Iterable<Taco> recentTacos() {
//        PageRequest page = PageRequest.of(
//                0, 12, Sort.by("createdAt").descending());
//        return tacoRepo.findAll(page);
//    }

//    @GetMapping("/recent")
//    public CollectionModel<EntityModel<Taco>> recentTacos() {
//        PageRequest page = PageRequest.of(
//                0, 12, Sort.by("createdAt").descending());
//        List<Taco> tacos = tacoRepo.findAll(page);
//        CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);
//        recentResources.add(linkTo(methodOn(DesignTacoApiController.class).recentTacos())
//                .withRel("recents"));
//        return recentResources;
//    }

//    @GetMapping("/recent")
//    public CollectionModel<TacoModel> recentTacos() {
//        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
//        List<Taco> tacos = tacoRepository.findAll(page);
//        List<TacoModel> tacoResources = (new TacoRepresentationModelAssembler()).toModels(tacos);
//        CollectionModel recentResources = CollectionModel.of(tacoResources);
//        recentResources.add(linkTo(methodOn(DesignTacoApiController.class).recentTacos())
//                        .withRel("recents"));
//        return recentResources;
//    }

    @GetMapping("/tacos/recent")
    public ResponseEntity<CollectionModel<TacoModel>> recentTacos(){
        PageRequest pageRequest = PageRequest.of(0,2, Sort.by("createAt").descending());
        Iterable<Taco> tacos = tacoRepository.findAll(pageRequest);
        CollectionModel<TacoModel> collections = new TacoModelAssembler().toCollectionModel(tacos);
        if (tacos.iterator().hasNext()){
            return new ResponseEntity<CollectionModel<TacoModel>>(collections, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optTaco = tacoRepository.findById(id);
        if (optTaco.isPresent()) {
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepository.save(taco);
    }
}
