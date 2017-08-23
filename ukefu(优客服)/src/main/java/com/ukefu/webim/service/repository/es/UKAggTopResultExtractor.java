package com.ukefu.webim.service.repository.es;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.ukefu.webim.web.model.UKAgg;


public class UKAggTopResultExtractor extends UKResultMapper{
	
	private String term  , name ;
	
	public UKAggTopResultExtractor(String term , String name){
		this.term = term ;
		this.name = name ;
	}

	@Override
	public <T> Page<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
		Aggregations aggregations = response.getAggregations();
		Terms agg = aggregations.get(term) ;
		long total = agg.getSumOfOtherDocCounts() ;
		List<T> results = new ArrayList<T>();
		if(agg.getBuckets()!=null && agg.getBuckets().size()>0){
			for (int i = pageable.getPageNumber()*pageable.getPageSize();i<agg.getBuckets().size()  ; i++) {
				Terms.Bucket entry = agg.getBuckets().get(i) ;
				if(!StringUtils.isBlank(name) && entry.getAggregations().get(name)!=null){
					TopHits topHits = entry.getAggregations().get(name);
					for (SearchHit hit : topHits.getHits().getHits()) {
						T data = mapEntity(hit.getSourceAsString() , hit , clazz) ;
						if(data instanceof UKAgg){
							((UKAgg) data).setRowcount((int) topHits.getHits().getTotalHits());
						}
						results.add(data) ;
					}
				}
			}
		}
		return new PageImpl<T>(results, pageable, total);
	}
}
