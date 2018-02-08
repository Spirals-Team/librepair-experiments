package Hello;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "AddressBook", path = "AddressBook")
public interface AddressBookRepository extends PagingAndSortingRepository<AddressBook, Long> {
}