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

  componentDidMount(){
    this.fetchData();
  }

  fetchData() {
    this.setState({
      dataSource: this.state.dataSource.cloneWithRows(
        [{ "value": "62", "feeling": "content"},
         { "value": "75", "feeling": "happy"},
         { "value": "83", "feeling": "angry"},
      ]
      ),
      loaded: true,
    });
    }

  renderMovie(pulse){
    return (
      <View>
      <Text style={{fontSize:20}}>Value: {pulse.value}  Feeling: {pulse.feeling}</Text>
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
                  <TouchableHighlight onPress={() => this.props.navigator.push({component: AddItem})}>
                  <View>
                    <Text style={styles.toolbarButton}>Log pulse</Text>
                  </View>
                  </TouchableHighlight>
            </View>
            <View>
              <PulseList />
            </View>
      </View>
    )
  }
}

class App extends Component {
  constructor(props)
  {
    super(props);
  }

  renderScene (route, navigator) {
    return <route.component {...route.passProps} navigator={navigator} />
  }

  render() {
    return (
      <Navigator
        initialRoute={{component: AppMain, index: 0}}
        renderScene={this.renderScene}/>
    );
  }
}

AppRegistry.registerComponent('PulseTrack', () => App);
