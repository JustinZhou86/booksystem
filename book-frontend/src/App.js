import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import BookList from './BookList';
import BookEdit from "./BookEdit";

class App extends Component {
  render() {
    return (
        <Router>
          <React.StrictMode>
          <Switch>
            <Route path='/' exact={true} component={BookList}/>
            <Route path='/books' exact={true} component={BookList}/>
            <Route path='/books/:id' component={BookEdit}/>
          </Switch>
          </React.StrictMode>
        </Router>
    )
  }
}

export default App;
