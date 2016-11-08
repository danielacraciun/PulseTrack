import React, { Component } from 'react';
import {
  Button,
  AppRegistry,
  StyleSheet,
  Text,
  ListView,
  View,
  TouchableHighlight,
  Alert,
  Navigator
} from 'react-native';

import { AddItem } from './addlog.js'

class PulseList extends Component {
  constructor(prop){
    super(prop);
    this.state={
      dataSource: new ListView.DataSource({
        rowHasChanged: (row1, row2) => row1 !== row2,
      }),
      loaded: false,
    };
  }
  componentWillReceiveProps(nextProps) {
    this.setState({
      dataSource: this.state.dataSource.cloneWithRows(
          this.props.content
      ),
      loaded: true,
    });
  }

  renderMovie(pulse){
    return (
      <View>
      <Text style={{fontSize:20,
          paddingBottom: 10,
          borderBottomWidth: 2,
          borderBottomColor: 'white',
          color: 'white',}}>
        Value: {pulse.value}  Feeling: {pulse.feeling}
      </Text>
      </View>
      );
  }

  render(){
    return (
      <ListView
      dataSource={this.state.dataSource}
      renderRow={this.renderMovie}
      style={styles.listView}
      />
    );
  }
}

export class AppMain extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <View>
            <View style={styles.toolbar}>
                <Text style={styles.toolbarTitle}>PulseTrack</Text>
                  <TouchableHighlight
                    onPress={() => this.props.navigator.push({
                      component: AddItem, passProps: {store: this.props.store}, index: 1})}>
                  <View>
                    <Text style={styles.toolbarButton}>Log pulse</Text>
                  </View>
                  </TouchableHighlight>
            </View>
            <View>
              <PulseList content={this.props.store}/>
            </View>
      </View>
    )
  }
}

class App extends Component {
  constructor(props)
  {
    super(props);
    this.store = [
     { "value": "62", "feeling": "content"},
     { "value": "75", "feeling": "happy"},
     { "value": "83", "feeling": "angry"},
   ];
  }

  renderScene (route, navigator) {
    return <route.component {...route.passProps} navigator={navigator}/>
  }

  render() {
    return (
      <Navigator
        initialRoute={{component: AppMain, passProps: {store: this.store}, index: 0}}
        renderScene={this.renderScene}/>
    );
  }
}

var styles = StyleSheet.create({
    toolbar:{
        backgroundColor:'#81c04d',
        paddingTop:30,
        paddingBottom:10,
        flexDirection:'row'    //Step 1
    },
    toolbarButton:{
        width: 80,            //Step 2
    },
    toolbarTitle:{
        fontSize: 25,
        color:'#fff',
        textAlign: 'left',
        paddingLeft:10,
        fontWeight:'bold',
        flex:1                //Step 3
    },
    listView: {
        backgroundColor: 'green',
    },
});

AppRegistry.registerComponent('PulseTrack', () => App);
