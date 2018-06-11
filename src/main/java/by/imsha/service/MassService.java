package by.imsha.service;

import by.imsha.domain.Mass;
import by.imsha.repository.MassRepository;
import by.imsha.utils.ServiceUtils;
import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static by.imsha.utils.Constants.LIMIT;
import static by.imsha.utils.Constants.PAGE;

/**
 * @author Alena Misan
 */

@Service
public class MassService {
    private static Logger logger = LoggerFactory.getLogger(MassService.class);

    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();

    private MongoVisitor mongoVisitor = new MongoVisitor();

    @Autowired
    private MassRepository massRepository;

    public Mass createMass(Mass mass){
        return massRepository.save(mass);
    }

    public List<Mass> createMassesWithList(List<Mass> masses){
        return massRepository.save(masses);
    }

    public Mass getMassByParish(String parishId){
        return massRepository.findByParishId(parishId);
    }

    public List<Mass> getMassByCity(String cityId){
//        TODO check index for cityID and deleted.
        List<Mass> masses = massRepository.findByCityIdAndDeleted(cityId, false);
        if(CollectionUtils.isEmpty(masses)){
            if(logger.isWarnEnabled()){
                logger.warn(String.format("No masses found with city id = %s, and deleted = %s", cityId, Boolean.FALSE));
            }
        }
        return masses;
    }

    public Mass getMass(String id){
        return massRepository.findOne(id);
    }

    public List<Mass> search(String filter){
        // TODO can be added default sorting
        return search(filter, PAGE, LIMIT, null);
    }

    public List<Mass> search(String filter, int offset, int limit, String sort){
        Query query = prepareQuery(filter, offset, limit, sort);
        if (query == null) return null;
        List<Mass> masses = this.massRepository.search(query, Mass.class);
        return masses;
    }

    public Query prepareQuery(String filter, int offset, int limit, String sort) {
        if(StringUtils.isBlank(filter)){
            if(logger.isInfoEnabled()){
                logger.info("No searching masses: query is blank");
            }
            return null;
        }
        int page = PAGE;
        int limitPerPage = LIMIT;
        if(offset > 0 && limit > 0){
            if(offset % limit == 0){
                page = offset;
                limitPerPage = limit;
            }else{
                page = PAGE;
                limitPerPage = limit;
            }
        }else if( limit < 0 && offset > 0 && offset % LIMIT == 0){
            page = offset;
            limitPerPage = LIMIT;
        } else if(offset < 0 && limit > 0 && PAGE % limit == 0 ){
            page = PAGE;
            limitPerPage = limit;
        }

        Condition<GeneralQueryBuilder> condition = pipeline.apply(filter, Mass.class);
        Criteria criteria = condition.query(mongoVisitor);
        Query query = new Query();
        query.addCriteria(criteria);
        query.with(new PageRequest(page, limitPerPage));
        String[] sortValue = ServiceUtils.parseSortValue(sort);
        if(sortValue != null){
            Sort.Direction direction = sortValue[1].equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
            query.with(new Sort(direction, sortValue[0]));
        }
        return query;
    }


//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Mass updateMass(Mass mass){
        return massRepository.save(mass);
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void removeMass(String id){
        massRepository.delete(id);
    }


}
