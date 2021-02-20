import React, { Component } from 'react';
import FilterElement from './FilterElement'

function Filter({title, filter_list,setFilter}){
    const filters = filter_list.map(filter => {
        console.log(filter)
        return <FilterElement filter_element={filter} setFilter={setFilter}/>
    })
    return(
        <div className="filter card">
            <div className="filter_header card-header">
                <button className="btn btn-link row mw-100" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseFilterSubject">
                    <span className="col-5">{title}</span>
                </button>
            </div>
            <ul className="filter_list card-body filter_subject collapse show" id="collapseFilterSubject">
                {filters}
            </ul>
        </div>
    )

}

export default Filter