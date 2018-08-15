package org.opensrp.stock.openlmis.repository;

import org.opensrp.stock.openlmis.domain.Orderable;
import org.opensrp.stock.openlmis.domain.OrderableExample;
import org.opensrp.stock.openlmis.repository.mapper.OrderableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

import static org.opensrp.stock.openlmis.util.Utils.getCurrentTime;

@Repository
public class OrderableRepository implements BaseRepository<Orderable> {

    @Autowired
    private OrderableMapper orderableMapper;

    @Override
    public void add(Orderable orderable) {

        if (orderable == null || orderable.getId() == null) {
            return;
        }
        // Orderable already exists
        if (retrievePrimaryKey(orderable) != null) {
            return;
        }
        orderable.setServerVersion(getCurrentTime());
        orderableMapper.insert(orderable);
    }

    @Override
    public Orderable get(Object id) {
        return orderableMapper.selectByPrimaryKey((String) id);
    }

    public List<Orderable> get(String id, String tradeItemId, String commodityTypeId) {

        OrderableExample orderableExample = new OrderableExample();
        orderableExample.createCriteria().andIdEqualTo(id).andTradeItemIdEqualTo(tradeItemId).andCommodityTypeIdEqualTo(commodityTypeId);
        return orderableMapper.selectByExample(orderableExample);
    }

    public List<Orderable> get(long prevServerVersion) {

        OrderableExample orderableExample = new OrderableExample();
        orderableExample.createCriteria().andServerVersionBetween(prevServerVersion, getCurrentTime());
        return orderableMapper.selectByExample(orderableExample);
    }

    @Override
    public void update(Orderable orderable) {
        orderable.setServerVersion(getCurrentTime());
        orderableMapper.updateByPrimaryKey(orderable);
    }

    @Override
    public List<Orderable> getAll() {

        OrderableExample orderableExample = new OrderableExample();
        orderableExample.createCriteria();
        return orderableMapper.selectByExample(orderableExample);
    }

    @Override
    public Long safeRemove(Orderable orderable) {

        if (orderable == null || orderable.getId() == null) {
            return null;
        }

        if (orderable.getDateDeleted() == null) {
            orderable.setDateDeleted( Calendar.getInstance().getTimeInMillis());
        }

        OrderableExample orderableExample = new OrderableExample();
        orderableExample.createCriteria().andIdEqualTo(orderable.getId()).andDateDeletedIsNull();
        orderableMapper.updateByExample(orderable, orderableExample);

        return orderable.getDateDeleted();
    }

    private String retrievePrimaryKey(Orderable orderable) {

        Object uniqueId = getUniqueField(orderable);
        if (uniqueId == null) {
            return null;
        }

        String id = uniqueId.toString();

        OrderableExample orderableExample = new OrderableExample();
        orderableExample.createCriteria().andIdEqualTo(id);

        List<Orderable> result = orderableMapper.selectByExample(orderableExample);
        if (result.size() == 0) {
            return null;
        }
        return orderable.getId().toString();
    }

    private Object getUniqueField(Orderable orderable) {

        if (orderable == null) {
            return null;
        }
        return orderable.getId().toString();
    }
}
