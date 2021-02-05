import React from 'react';

function InfoElement({k, v}){
    if (k == '_no') return null
    if (k == 'major'){
        let major_list = ['컴퓨터과학부', '경영학부', '화학공학과', '철학과']
        let major_componet = major_list.map(major => {
            if(v === major)
                return  <option value={major} selected>{major}</option>
            else
                return  <option value={major}>{major}</option>
        })
        return(
            <div className="row info_element mb-3 ">
                <div className="col-sm-5 key">
                    {k}
                </div>
                <div className="col-sm-7 value">
                    <select className="form-control mx-3" labelId="simple-select-label" name="major" disabled>
                        {major_componet}
                    </select>                
                </div>
            </div>
        )
    }
    else{
        var type = "text"
        if(k == 'password') type = "password"

        return(
            <div className="row info_element mb-3 ">
                <div className="col-sm-5 key">
                    {k}
                </div>
                <div className="col-sm-7 value">
                    <input name={k} className="form-control mod_input input_id mx-3" type={type} placeholder={v} readOnly/>    
                </div>
            </div>
        )
    }
}


export default InfoElement