
import React,{Component} from 'react'
import Login from './page/Login'
import Main from './page/Main'
import MyInfo from './page/MyInfo'
import makePB from './page/makePB'
import Problem from './page/Problem'
import SolveList from './page/SolveList'
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from 'react-router-dom'

class App extends Component{
  render(){
    return (
      <Router>
        <Switch>
          <Route exact path="/" component={Login} />
          <Route exact path="/main" component={Main}/>
          <Route exact path="/myInfo" component={MyInfo}/>
          <Route exact path="/makePB" component={makePB}/>
          <Route exact path="/problem/:id" component={Problem}/>  
          <Route exact path="/solveList" component={SolveList}/>     
        </Switch>
      </Router>  
    )
  }
}

export default App
