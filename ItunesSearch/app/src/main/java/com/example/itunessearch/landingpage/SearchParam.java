package com.example.itunessearch.landingpage;

public class SearchParam {
    private SearchState searchState;
    private String searchText;

    public SearchParam(SearchState searchState, String searchText) {
        this.searchState = searchState;
        this.searchText = searchText;
    }

    public SearchState getSearchState() {
        return searchState;
    }

    public void setSearchState(SearchState searchState) {
        this.searchState = searchState;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
