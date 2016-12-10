import React, { Component } from 'react';
import {
  Button,
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableHighlight,
  Alert,
  Navigator
} from 'react-native';

import { AddItem } from './addlog.js'
import { Statistics } from './stats.js'
import { EditItem } from './editlog.js'
import { CustomButton } from './button.js'
import { ListView } from 'realm/react-native';
import realm from './models';

class PulseList extends Component {
  constructor(prop){
    super(prop);
    this.state={
      dataSource: new ListView.DataSource({
        rowHasChanged: (row1, row2) => row1 !== row2,
      }),
      loaded: false,
    };

    let elements = this.props.content;
    var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2 });

    realm.objects('Pulse').addListener((pulses, changes) => {
      this.setState({dataSource: this.state.dataSource.cloneWithRows(pulses)})
    });

    this.state = {
      dataSource: ds.cloneWithRows(elements),
      data: elements
    };
  }

  async deleteLog(log) {
    realm.write(() => {
      realm.delete(log)
    })
  }

  renderPulse(pulse, navigator) {
    return (
      <View>
      <Text style={{fontSize:20,
          color: 'white',}}>
        Value: {pulse.value}  Feeling: {pulse.feeling}
      </Text>
      <CustomButton
        name='EDIT'
        onPress={() => navigator.push({
          component: EditItem, passProps: {item:pulse}, index: 2})}
        />
        <CustomButton
          name='DELETE'
          onPress= {() => Alert.alert(
            'Delete',
            "Are you sure you want to delete this log?",
            [{text: 'Cancel', onPress: () => console.log('Cancel Pressed!')},
            {text: 'OK', onPress: () => this.deleteLog(pulse)}]
          )}
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
      renderRow={(rowData, navigator) => this.renderPulse(rowData, this.props.navigator)}
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
                    onPress={() => this.props.navigator.push({component: AddItem, index: 1})}>
                  <View>
                    <Text style={styles.toolbarButton}>Log pulse</Text>
                  </View>
                  </TouchableHighlight>

                  <TouchableHighlight
                    onPress={() => this.props.navigator.push({component: Statistics, index: 1})}>
                  <View>
                    <Text style={styles.toolbarButton}>Stats</Text>
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
    this.store = realm.objects('Pulse');
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
