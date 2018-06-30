package pl.sda.jira.forum.domain;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forum")
public class ForumController {
    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ForumDto read(@PathVariable String id) {
        return forumService.get(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String create(@ModelAttribute ForumDto forumDto) {
        return forumService.add(forumDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}")
    public String update(@ModelAttribute ForumDto forumDto, @PathVariable String id) {
        forumService.update(id, forumDto);
        return "Forum name has been changed: " + forumDto.getName();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{forumId}")
    public void delete(@ModelAttribute ForumDto forumDto, @PathVariable String forumId) {
        forumService.remove(forumId);
    }
}