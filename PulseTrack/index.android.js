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
import { EditItem } from './editlog.js'
import { CustomButton } from './button.js'

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
    var newItems = [];
    for (var i=0; i < nextProps.content.length; i++) {
        var newItem = {}
        newItem["value"] = nextProps.content[i].value;
        newItem["feeling"] = nextProps.content[i].feeling;
        newItems.push(newItem);
    }
    this.setState({
      dataSource: this.state.dataSource.cloneWithRows(
          newItems
      ),
      loaded: true,
    });
  }

  componentDidMount(){
    this.setState({
      dataSource: this.state.dataSource.cloneWithRows(
          this.props.content
      ),
      loaded: true,
    });
  }

  renderPulse(pulse, navigator, store) {
    return (
      <View>
      <Text style={{fontSize:20,
          color: 'white',}}>
        Value: {pulse.value}  Feeling: {pulse.feeling}
      </Text>
      <CustomButton
        name='EDIT'
        onPress={() => navigator.push({
          component: EditItem, passProps: {store: store, item:pulse}, index: 2})}
        />
      <Text style={{
          borderBottomWidth: 2,
          borderBottomColor: 'white',
          color: 'white',}} />
      </View>
      );
  }

  render(){
    return (
      <ListView
      dataSource={this.state.dataSource}
      renderRow={(rowData, navigator, store) => this.renderPulse(rowData, this.props.navigator, this.props.content)}
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
              <PulseList content={this.props.store} navigator={this.props.navigator}/>
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
        flexDirection:'row'
    },
    toolbarButton:{
        width: 80,
    },
    toolbarTitle:{
        fontSize: 25,
        color:'#fff',
        textAlign: 'left',
        paddingLeft:10,
        fontWeight:'bold',
        flex:1
    },
    listView: {
        backgroundColor: 'green',
    },
});

AppRegistry.registerComponent('PulseTrack', () => App);
