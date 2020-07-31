package com.txstate.edu.homeServices.service;

import com.txstate.edu.homeServices.model.CustomerRating;
import com.txstate.edu.homeServices.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RankingService {

    @Autowired
    ReviewRepository reviewRepository;

    final int BASE_SCORE = 100;
    final double NO_CUSTOMER_REIVEW_WEIGHT = 0.60;
    final double CUSTOMER_RATING_WEIGHT = 0.40;

    public List<CustomerRating> getContractorRanking()
    {

        List<CustomerRating>  customerRatingsBase = reviewRepository.display_Contractor_List();
        List<CustomerRating>  customerRatingsProcessed = new ArrayList<>();

        long maxReview = Integer.MIN_VALUE;

        for (CustomerRating customerRating: customerRatingsBase) {
            maxReview = (maxReview<customerRating.getCount())? customerRating.getCount() : maxReview;
        }

        for (CustomerRating customerRating: customerRatingsBase) {
            customerRating.setScore(calculateScore(customerRating));
            customerRating.setMax(maxReview);
            customerRatingsProcessed.add(customerRating);
        }

        Collections.sort(customerRatingsProcessed, new Comparator<CustomerRating>() {
            @Override
            public int compare(CustomerRating c1, CustomerRating c2) {
                return (int) (c2.getScore()-c1.getScore());
            }
        });

        int rank = 1;
        for (CustomerRating customerRating: customerRatingsProcessed) {
          customerRating.setRank(rank++);
        }

        return customerRatingsProcessed;
    }

    public long calculateScore(CustomerRating cR){
        long score = BASE_SCORE;
        score += cR.getCount()*NO_CUSTOMER_REIVEW_WEIGHT;
        score += cR.getRating()*CUSTOMER_RATING_WEIGHT;

        return  score;
    }
}
