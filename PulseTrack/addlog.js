import React, { Component } from 'react';
import {
  Text,
  TextInput,
  Navigator,
  View,
  DatePickerAndroid
} from 'react-native';
import { CustomButton } from './button.js'
import realm from './models';

export class AddItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pulse: 'pulse value',
      feeling: 'how are you feeling?',
      loggedAt: new Date()
    };
  }

  getNextId() {
    let nextId = realm.objects('Pulse').sorted('id');
    if (nextId.length > 0) {
      return nextId[nextId.length - 1].id + 1;
    }
    return 1;
  }

  addItem() {
    if (this.state.pulse != ''&& this.state.feling != '' && this.state.pulse == parseInt(this.state.pulse, 10))
    {
      realm.write(() => {
        realm.create('Pulse',
        {id: this.getNextId(),
          value: parseInt(this.state.pulse),
          feeling: this.state.feeling,
          loggedAt: this.state.loggedAt});
      });
      // Then return to first page after inserting
      this.props.navigator.pop();
    } else {
      alert("Something is not right!")
    }
  }

  datePick() {
  }

  render() {
    return(
      <View>
        <Text>LOG PULSE</Text>
          <View>
            <TextInput value={this.state.pulse}
               onFocus={() => this.setState({pulse: ''})}
               onChangeText={(pulse) => this.setState({pulse})}/>
             <TextInput value={this.state.feeling}
                  onFocus={() => this.setState({feeling: ''})}
                  onChangeText={(feeling) => this.setState({feeling})}/>
          </View>
          <View>
            <CustomButton name='CHOOSE DATE' onPress={() => this.datePick()}/>
            <CustomButton name='RETURN' onPress={() => this.props.navigator.pop()}/>
            <CustomButton name='SUBMIT LOG' onPress={() => this.addItem()}/>
          </View>
        </View>
    );
  }
}
