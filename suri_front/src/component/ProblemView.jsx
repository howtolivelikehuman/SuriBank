import React from 'react'

const setPB = (data, subjectList) => {
    if(!data || !subjectList) return 
    let problem =[]
    let value
    for(var key in data){
        if (key==='title'||key ==='question' || key === 'answer' || key === 'id') continue
        if (key==='subject'){
            value = get_subject_name(subjectList, data[key])
        }
        else value=data[key]
        problem.push(
            <div className="row info_element mb-3">
                <div className="col-sm-5 key">
                    {key}
                </div>
                <div className="col-sm-7 value">
                    {value}
                </div>
            </div>
        )
    }
    console.log(problem)
    return problem
}

class ProblemView extends React.Component{
    render(){
        const {data, subjectList} = this.props
        const problem = setPB(data,subjectList)
        return(
            data &&
            <div className="card mb-5">
                <div className="card-header">
                    <div className="row">
                        <h2 className="col-11">{data['title']}</h2>
                    </div>
                </div>
                <div className="card-body px-5">
                    {problem}
                    <div className="my-3">
                        <div className="key">question</div>
                        <div className="value my-2">
                            {data['question']}
                        </div>
                    </div>
                    <div name="answer" className="invisible">
                        <div className="key">answer</div>
                        <div className="value my-2">
                            {data['answer']}
                        </div>
                    </div>
                    <div className="row">
                        <button 
                            type="button" 
                            className="p-2 col-10 mx-auto border-0 bg-light rounded rounded-pill shadow-sm my-4btn btn-secondary" 
                            onClick={()=>view_answer_handler()}
                        >view answer</button>
                    </div>
                </div>
            </div>
            
        )
    }
}



const view_answer_handler = () => {
    let isVisible = document.getElementsByName('answer')[0].className
    if(isVisible === "invisible") document.getElementsByName('answer')[0].className = 'visible'
    else document.getElementsByName('answer')[0].className = 'invisible'
}

const get_subject_name = (subjectList, code) => {
    const subject = subjectList.find(subject => subject['code'] === code)
    return subject['name']
}

export default ProblemView