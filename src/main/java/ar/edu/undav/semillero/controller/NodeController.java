package ar.edu.undav.semillero.controller;

import ar.edu.undav.semillero.domain.entity.Node;
import ar.edu.undav.semillero.request.CreateNodeRequest;
import ar.edu.undav.semillero.service.NodeService;
import ar.edu.undav.semillero.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/node")
@CrossOrigin
public class NodeController {

    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    // Guardar un nodo
    @PostMapping("")
    public Node saveNode(@Valid @RequestBody CreateNodeRequest request) {
        return nodeService.save(request);
    }

    // Obtener nodos por ID
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Node> getNode(@PathVariable long id) {
        return WebUtils.emptyToNotFound(nodeService.findById(id));
    }

    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Node> update(@PathVariable long id, @Valid @RequestBody CreateNodeRequest request) {
        return WebUtils.emptyToNotFound(nodeService.update(id, request));
    }

    // Obtener todos los nodos
    @JsonView(View.Summary.class)
    @GetMapping
    public Collection<Node> getNode() {
        return nodeService.findAll();
    }
}
