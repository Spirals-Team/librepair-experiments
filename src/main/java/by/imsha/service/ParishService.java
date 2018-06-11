package by.imsha.service;


import by.imsha.domain.Parish;
import by.imsha.domain.dto.ParishInfo;
import by.imsha.domain.dto.mapper.ParishInfoMapper;
import by.imsha.repository.ParishRepository;
import by.imsha.utils.ServiceUtils;
import com.github.rutledgepaulv.qbuilders.builders.GeneralQueryBuilder;
import com.github.rutledgepaulv.qbuilders.conditions.Condition;
import com.github.rutledgepaulv.qbuilders.visitors.MongoVisitor;
import com.github.rutledgepaulv.rqe.pipes.QueryConversionPipeline;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import static by.imsha.utils.Constants.*;

@Service
public class ParishService {

    private static ParishService INSTANCE;

    @PostConstruct
    public void initInstance(){
        INSTANCE = this;
    }

    public static ParishInfo extractParishInfo(String parishId){
        Parish parish = INSTANCE.getParish(parishId);
        return ParishInfoMapper.MAPPER.toParishInfo(parish);
    };


    private static Logger logger = LoggerFactory.getLogger(ParishService.class);

    private QueryConversionPipeline pipeline = QueryConversionPipeline.defaultPipeline();

    private MongoVisitor mongoVisitor = new MongoVisitor();

    @Autowired
    ParishRepository parishRepository;

    public Parish createParish(Parish parish){
        return parishRepository.save(parish);
    }

    public List<Parish> createParishesWithList(List<Parish> parishes){
        return parishRepository.save(parishes);
    }

    public Parish getParishByUser(String userId){
        return parishRepository.findByUserId(userId);
    }

    public Parish getParish(String id){
        return parishRepository.findOne(id);
    }

    public List<Parish> search(String filter){
        // TODO can be added default sorting
        return search(filter, PAGE, LIMIT, null);
    }

    public List<Parish> search(String filter, int offset, int limit, String sort){
        if(StringUtils.isBlank(filter)){
            if(logger.isInfoEnabled()){
                logger.info("No searching parishes: query is blank");
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

        Condition<GeneralQueryBuilder> condition = pipeline.apply(filter, Parish.class);
        Criteria criteria = condition.query(mongoVisitor);
        Query query = new Query();
        query.addCriteria(criteria);
        query.with(new PageRequest(page, limitPerPage));
        String[] sortValue = ServiceUtils.parseSortValue(sort);
        if(sortValue != null){
            Sort.Direction direction = sortValue[1].equals("+") ? Sort.Direction.ASC : Sort.Direction.DESC;
            query.with(new Sort(direction, sortValue[0]));
        }
        List<Parish> parishes = this.parishRepository.search(query, Parish.class);
        return parishes;
    }



//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Parish updateParish(Parish parish){
        return parishRepository.save(parish);
    }

    //TODO enable for production env
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void removeParish(String id){
        parishRepository.delete(id);
    }


}
