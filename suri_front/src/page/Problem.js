import React, { useEffect, useState } from 'react';
import api from '../util/API'
import { IoTimeSharp } from 'react-icons/io5';
import SubHeader from '../component/SubHeader'
import ProblemScore from '../component/ProblemScore'
import ProblemView from '../component/ProblemView'
import SolveList from '../component/SolveList'
import SolveInput from '../component/SolveInput'

const Problem = (props) => {
    const [data, setData] = useState(null)
    const [subjectList, setSubjectList] = useState(null)
    const [rate, setRate] = useState(5)
    const [solveInput, setSolveInput] = useState(null)
    const problemId = props.location.data.id
    console.log(problemId)

    const post_solve = () => {
        api.post(`solve/${problemId}`, {
            score: rate,
            userAnswer: solveInput,
        })
        .then(res => {
            if(res.status===200) 
                alert('별점 및 답안이 반영되었습니다!')
        })
        .catch(err => alert(err))
    }

    const get_pb_data = () => {
        api.get(`problem/${problemId}`)
        .then(res => {
            console.log('pb:',res.data)
            setData(res.data)
        })
    }

    const get_pb_data_for_test =()=>{
        let problem=
            {
                "uploader":"카와잇규짱",
                "title": "리액트 뭐냐고 어렵다고",
                "subject": "리액트",
                "type" : "예제",
                "professor": "엄교수",
                "question" :"이미지 넣기 어려워!이미지 넣기 어려워!이미지 넣기 어려워!이미지 넣기 어려워!이미지 넣기 어려워!",
                "answer" : "새해 복 많이 받으세요!새해 복 많이 받으세요!새해 복 많이 받으세요!새해 복 많이 받으세요!새해 복 많이 받으세요!" ,
                "registerdate":"2021-2-14",
                "score": 4.5,
                "hit": 12
            }

        setData(problem)
    }

    const get_subject_list = () => {
        api.get('/problem/subjectList')
        .then(res => {
            setSubjectList(res.data)
        })
    }

    useEffect(()=>{
        get_pb_data()
        get_subject_list()
    },[])


    return(
        <div>
            <SubHeader/>
            <div className="container">
                <ProblemView data={data} subjectList={subjectList} />
                <ProblemScore rate={rate} set_rate={setRate}/>
                <SolveInput solveInput={solveInput} setSolveInput={setSolveInput}/>
                <div className="row"><button onClick={post_solve} className="btn btn-primary mx-3 mw-100">별점주기</button></div>
                <SolveList problemId={problemId}/>
            </div>
        </div>
    )
}

export default Problem