import React, { Component } from 'react';

function InfoElement({k, v}){
    if (k=='_no'||k=='password') return ""
    else{
        return(
            <div className="row info_element mb-3 ">
                <div className="col-sm-7 key">
                    {k}
                </div>
                <div className="col-sm-5 value">
                    <input>{v}</input>
                </div>
            </div>
        )
    }
}


export default InfoElement