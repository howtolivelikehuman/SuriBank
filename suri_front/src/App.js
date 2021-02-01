
import React,{Component} from 'react'
import Login from './Login'
import Main from './Main'
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
        </Switch>
      </Router>  
    )
  }
}

export default App
