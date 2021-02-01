import React, { Component } from 'react';

function InfoElement({k, v}){
    return(
        <div className="row info_element">
            <div className="col-sm-7 key">
                {k}
            </div>
            <div className="col-sm-5 value">
                {v}
            </div>
        </div>
    )
}


export default InfoElement