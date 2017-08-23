package com.ukefu.webim.service.repository.es;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import com.ukefu.core.UKDataContext;
import com.ukefu.util.UKTools;
import com.ukefu.webim.web.model.QuickReply;

@Component
public class QuickReplyRepositoryImpl implements QuickReplyEsCommonRepository{
	private ElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	public void setElasticsearchTemplate(ElasticsearchTemplate elasticsearchTemplate) {
        this.elasticsearchTemplate = elasticsearchTemplate ;
    }
	@Override
	public Page<QuickReply> getByOrgiAndCate(String orgi ,String cate , String q, Pageable page) {

		Page<QuickReply> pages  = null ;
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(termQuery("cate" , cate)) ;
		
	    if(!StringUtils.isBlank(q)){
	    	boolQueryBuilder.must(new QueryStringQueryBuilder(q).defaultOperator(Operator.AND)) ;
	    }
	    NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withSort(new FieldSortBuilder("createtime").unmappedType("date").order(SortOrder.DESC));
	    searchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("title").fragmentSize(200)) ;
	    SearchQuery searchQuery = searchQueryBuilder.build().setPageable(page) ;
	    if(elasticsearchTemplate.indexExists(QuickReply.class)){
	    	pages = elasticsearchTemplate.queryForPage(searchQuery, QuickReply.class , new UKResultMapper());
	    }
	    return pages ; 
	}
	
	@Override
	public List<QuickReply> findByOrgiAndCreater(String orgi ,String creater , String q) {

		List<QuickReply> pages  = null ;
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(termQuery("orgi" , orgi)) ;
		
		BoolQueryBuilder quickQueryBuilder = QueryBuilders.boolQuery();
		
		quickQueryBuilder.should(termQuery("type" , UKDataContext.QuickTypeEnum.PUB.toString())) ;
		
		BoolQueryBuilder priQueryBuilder = QueryBuilders.boolQuery();
		
		priQueryBuilder.must(termQuery("type" , UKDataContext.QuickTypeEnum.PRI.toString())) ;
		priQueryBuilder.must(termQuery("creater" , creater)) ;
		
		quickQueryBuilder.should(priQueryBuilder) ;
		
		boolQueryBuilder.must(quickQueryBuilder) ;
		
	    if(!StringUtils.isBlank(q)){
	    	boolQueryBuilder.must(new QueryStringQueryBuilder(q).defaultOperator(Operator.AND)) ;
	    }
	    NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withSort(new FieldSortBuilder("createtime").unmappedType("date").order(SortOrder.DESC));
	    searchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("title").fragmentSize(200)) ;
	    SearchQuery searchQuery = searchQueryBuilder.build() ;
	    if(elasticsearchTemplate.indexExists(QuickReply.class)){
	    	pages = elasticsearchTemplate.queryForList(searchQuery, QuickReply.class);
	    }
	    return pages ; 
	}
	
	
	@Override
	public Page<QuickReply> getByQuicktype(String quicktype , final int p , final int ps) {

		Page<QuickReply> pages  = null ;
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(termQuery("type" , quicktype)) ;
		
	    NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withSort(new FieldSortBuilder("createtime").unmappedType("date").order(SortOrder.DESC));
	    
	    searchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("title").fragmentSize(200)) ;
	    SearchQuery searchQuery = searchQueryBuilder.build().setPageable(new PageRequest(p, ps)) ;
	    if(elasticsearchTemplate.indexExists(QuickReply.class)){
	    	pages = elasticsearchTemplate.queryForPage(searchQuery, QuickReply.class , new UKResultMapper());
	    }
	    return pages ; 
	}
	
	@Override
	public Page<QuickReply> getByCateAndUser(String cate  , String q , String user ,final int p , final int ps) {

		Page<QuickReply> pages  = null ;
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(termQuery("cate" , cate)) ;
		
	    if(!StringUtils.isBlank(q)){
	    	boolQueryBuilder.must(new QueryStringQueryBuilder(q).defaultOperator(Operator.AND)) ;
	    }
		
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withQuery(termQuery("creater" , user)).withSort(new FieldSortBuilder("top").unmappedType("boolean").order(SortOrder.DESC)).withSort(new FieldSortBuilder("updatetime").unmappedType("date").order(SortOrder.DESC));
		SearchQuery searchQuery = searchQueryBuilder.build().setPageable(new PageRequest(p, ps));
		if(elasticsearchTemplate.indexExists(QuickReply.class)){
			pages = elasticsearchTemplate.queryForPage(searchQuery, QuickReply.class, new UKResultMapper());
	    }
	    return pages ; 
	}
	
	@Override
	public Page<QuickReply> getByCon(BoolQueryBuilder boolQueryBuilder, final int p , final int ps) {

		Page<QuickReply> pages  = null ;
		
		BoolFilterBuilder beginFilter = FilterBuilders.boolFilter().should(FilterBuilders.missingFilter("begintime") , FilterBuilders.rangeFilter("begintime").lte(UKTools.dateFormate.format(new Date()))) ;
		BoolFilterBuilder endFilter = FilterBuilders.boolFilter().should(FilterBuilders.missingFilter("endtime") , FilterBuilders.rangeFilter("endtime").gte(UKTools.dateFormate.format(new Date()))) ;
		
		
		FilteredQueryBuilder query = QueryBuilders.filteredQuery(
				boolQueryBuilder, 
	                FilterBuilders.boolFilter()
	                .must(beginFilter).must(endFilter));
		
	    NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(query).withSort(new FieldSortBuilder("createtime").unmappedType("date").order(SortOrder.DESC));
	    
	    SearchQuery searchQuery = searchQueryBuilder.build().setPageable(new PageRequest(p, ps)) ;
	    if(elasticsearchTemplate.indexExists(QuickReply.class)){
	    	pages = elasticsearchTemplate.queryForPage(searchQuery, QuickReply.class);
	    }
	    return pages ; 
	}
	@Override
	public Page<QuickReply> getByOrgi(String orgi , String q , Pageable page) {
		
		Page<QuickReply> list  = null ;
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(termQuery("orgi" , orgi)) ;
		
		
	    if(!StringUtils.isBlank(q)){
	    	boolQueryBuilder.must(new QueryStringQueryBuilder(q).defaultOperator(Operator.AND)) ;
	    }
		
		NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withSort(new FieldSortBuilder("createtime").unmappedType("date").order(SortOrder.DESC));
		SearchQuery searchQuery = searchQueryBuilder.build().setPageable(page);
		if(elasticsearchTemplate.indexExists(QuickReply.class)){
			list = elasticsearchTemplate.queryForPage(searchQuery, QuickReply.class);
	    }
	    return list ; 
	}
}
