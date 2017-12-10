package com.university.schedule.DAO;

import com.university.schedule.model.Department;
import com.university.schedule.model.Position;
import javafx.geometry.Pos;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PositionDAO")
public class PositionDAOImpl extends AbstractDAO<Integer, Position> implements PositionDAO {
    @Override
    public Position findById(int id) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("id",id));
        return (Position) criteria.uniqueResult();
    }

    @Override
    public Position findByName(String name) {
        Criteria criteria = createEntityCreteria();
        criteria.add(Restrictions.eq("name", name));
        return (Position) criteria.uniqueResult();
    }

    @Override
    public void save(Position position) {
        getSession().persist(position);
    }

    @Override
    public void delete(int id) {
        Query query = getSession().createSQLQuery("DELETE FROM position WHERE id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Position> findAll() {
        Criteria criteria = createEntityCreteria();
        return criteria.list();
    }

}
