import React from 'react';

function FilterElement({title,filter_element,setFilter}){
    return(
        <li className="checkbox-wrap">
            <input type="checkbox" id={filter_element} data-filter="" data-filter-type="" onChange={()=>setFilter(title, filter_element)}></input>
            <label className="ml-3" for={filter_element}><span>{filter_element}</span></label>
        </li>
    )
}


export default FilterElement