import React from 'react';


function ProblemElement({k, set_img}){
    if (k == 'uploader_id') return null
    if (k == 'type'){
        let type_list = ['기출', '예제'] //TO DO:기출 아닌거 뭐라 정하지 출제?
        let type_componet = type_list.map(type => (
            <option value={type}>{type}</option>
        ))
        return(
            <div className="row info_element mb-3 ">
                <div className="col-sm-5 key">
                    {k}
                </div>
                <div className="col-sm-7 value">
                    <select className="form-control mx-3" labelId="simple-select-label" name="type">
                        {type_componet}
                    </select>                
                </div>
            </div>
        )
    }

    if( k == 'title' || k == 'subject' || k == 'professor'){
        let ph=""
        if(k === 'title') ph="년도 + 제목 기입"
        return(
            <div className="row info_element mb-3 ">
                <div className="col-sm-5 key">
                    {k}
                </div>
                <div className="col-sm-7 value">
                    <input name={k} className="form-control mod_input mx-3" type="text" placeholder={ph} />    
                </div>
            </div>
        )
    }
    else{
        //https://basketdeveloper.tistory.com/55
        const file_input_handler=(e)=>{
            set_img(e.target.files[0])
        }
        return(
            <div className="info_element my-3">
                <div className="key">
                    {k}
                </div>
                <div className="value my-2">
                    <textarea name={k} rows="10" className="form-control mod_input" type="text" />    
                </div>            
            <div className="row info_element mb-3 custom-file mx-1" >
                <input type='file' className="custom-file-input" name='img_file' onChange={(e)=>file_input_handler(e)}/>
                <label className="custom-file-label" for='img_file'>이미지 선택</label>
            </div>
            </div>
        )
    }
}


export default ProblemElement