import React, { Component } from 'react';

class FilterList extends Component{
    render(){
        return(
            <li className="checkbox-wrap">
                <input type="checkbox" id="test1" data-filter="" data-filter-type=""></input>
                <label className="ml-3" for="test1"><span>test1</span></label>
            </li>
        )
    }
}

export default FilterList