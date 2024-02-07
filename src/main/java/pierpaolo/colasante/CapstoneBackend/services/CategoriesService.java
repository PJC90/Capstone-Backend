package pierpaolo.colasante.CapstoneBackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pierpaolo.colasante.CapstoneBackend.entities.Categories;
import pierpaolo.colasante.CapstoneBackend.entities.Review;
import pierpaolo.colasante.CapstoneBackend.exceptions.NotFoundException;
import pierpaolo.colasante.CapstoneBackend.repositories.CategoriesDAO;

@Service
public class CategoriesService {
    @Autowired
    private CategoriesDAO categoriesDAO;
    public Page<Categories> findAllCategories(int size, int page, String order){
        Pageable pageable= PageRequest.of(size, page, Sort.by(order));
        return categoriesDAO.findAll(pageable);
    }
    public Categories findById(int categoriesId){
        return categoriesDAO.findById(categoriesId).
                orElseThrow(()->new NotFoundException(categoriesId));}
}
