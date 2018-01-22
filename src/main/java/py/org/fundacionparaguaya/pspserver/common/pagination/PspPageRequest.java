package py.org.fundacionparaguaya.pspserver.common.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 *
 * @author bsandoval
 *
 */
public class PspPageRequest extends PageRequest {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PspPageRequest(int page, int size, String order, String sortBy) {
		super(page-1, size, ("asc".equals(order) ? Sort.Direction.ASC : Sort.Direction.DESC), sortBy);
	}

}
