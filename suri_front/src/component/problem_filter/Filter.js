import React, { Component } from 'react'
import FilterElement from './FilterElement'

function Filter({title, filter_list,setFilter}){
    const filters = filter_list.map(filter => {
        //console.log(filter)
        let filter_name = filter
        //if(title === 'type') filter_name = (filter === '기출' ? 'prev' : 'non_prev')
        return <FilterElement title={title} filter_element={filter_name} setFilter={setFilter}/>
    })
    return(
        <div className="filter card my-2">
            <div className="filter_header card-header">
                <div className="row"><span className="col-10 mx-auto">{title}</span></div>
            </div>
            <ul className="filter_list list-unstyled card-body filter_subject collapse show" id="collapseFilterSubject">
                {filters}
            </ul>
        </div>
    )

}

export default Filter