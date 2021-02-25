import React,{useState} from 'react'
import $ from "jquery";
import api from '../../util/API'


function ProblemElement({k, subject_data, set_q_img, set_a_img}){
    //console.log(k)
    if (k === 'uploader_id') return null
    if (k === 'type'){
        let type_list = ['기출', '예제'] //TO DO:기출 아닌거 뭐라 정하지 출제?
        let type_componet = type_list.map(type => (
            <option value={type==='기출'?'1':'0'}>{type}</option>
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
    if(k === 'subject'){
        let subject_component = subject_data.map(subject => 
                (<option value={subject['code']}>{subject['name']}</option>)
            )
            
        return(
            <div className="row info_element mb-3 ">
                <div className="col-sm-5 key">
                    {k}
                </div>
                <div className="col-sm-7 value">
                    <select className="form-control mx-3" labelId="simple-select-label" name="subject">
                        {subject_component}
                    </select>                
                </div>
            </div>
        )

    }

    if( k == 'title' || k == 'professor'){
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
        $(".custom-file-input").on("change", function() {
            var fileName = [] 
            for(var i =0; i < $(this).get(0).files.length; i++)
                fileName.push($(this).get(0).files[i].name+" ")
            $(this).siblings(".custom-file-label").addClass("selected").html(fileName)
          })
        //https://basketdeveloper.tistory.com/55
        const file_input_handler=(e)=>{
            if(e.target.id==='q') set_q_img(e.target.files)
            else set_a_img(e.target.files)
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
                <input type='file' id={k==='question'?'q':'a'} multiple="multiple" className="custom-file-input" name='img_file[]' onChange={(e)=>file_input_handler(e)}/>
                <label className="custom-file-label" for='img_file[]'>이미지 선택</label>
            </div>
            </div>
        )
    }
}


export default ProblemElement