import React, { useEffect, useState } from 'react';
import api from '../util/API'
import { IoTimeSharp } from 'react-icons/io5';
import SubHeader from '../component/SubHeader'
import ProblemScore from '../component/ProblemScore'
import ProblemView from '../component/ProblemView'
import SolveList from '../component/SolveList'
import SolveInput from '../component/SolveInput'
import MySolveView from '../component/MySolveView'
import { Route } from 'react-router';

const Problem = ({match}) => {
    const [data, setData] = useState(null)
    const [subjectList, setSubjectList] = useState(null)
    const [rate, setRate] = useState(5)
    const [solveInput, setSolveInput] = useState(null)
    const [mySolveData, setMySolveData] = useState(null)
    const problemId = match.params.id

    const post_solve = () => {
        api.post(`solve/${problemId}`, {
            score: rate,
            userAnswer: solveInput,
        })
        .then(res => {
            if(res.status===200){ 
                alert('별점 및 답안이 반영되었습니다!')
                getMySolve()
                //window.location.reload(); 
            }
        })
        .catch(err => alert(err))
    }

    const getMySolve = () => {
        api.get(`solve/check/${problemId}`)
        .then(res => {
            console.log(res.data)
            setMySolveData(res.data)
        })
        .catch(err => console.log(err))
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
        getMySolve()
    },[])


    return(
        <div>
            <SubHeader/>
            <div className="container">
                <ProblemView data={data} subjectList={subjectList} />
                {!mySolveData && <ProblemScore rate={rate} set_rate={setRate}/>}
                {!mySolveData && <SolveInput solveInput={solveInput} setSolveInput={setSolveInput}/>}
                {mySolveData && <MySolveView mySolveData = {mySolveData}/>}
                {!mySolveData && <div className="row"><button onClick={post_solve} className="btn btn-primary mx-3 mw-100">별점 및 풀이 제출</button></div>}
                <SolveList problemId={problemId}/>
            </div>
        </div>
    )
}

export default Problem