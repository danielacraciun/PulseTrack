import React, { Component } from 'react';
import {
  Text,
  TextInput,
  Navigator,
  View
} from 'react-native';
import { CustomButton } from './button.js'

export class EditItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pulse: props.item.value,
      feeling: props.item.feeling,
    };
    this.initState = this.state;
  }

  searchAndReplacePulse(item, newItem, myArray) {
    for (var i=0; i < myArray.length; i++) {
        if (myArray[i].value === item.pulse && myArray[i].feeling == item.feeling) {
            myArray[i].value = newItem.pulse;
            myArray[i].feeling = newItem.feeling;
            return true;
        }
    }
  }

  addItem() {
    if (this.state.pulse != ''&& this.state.feling != '' && this.state.pulse == parseInt(this.state.pulse, 10))
    {
      if (!this.searchAndReplacePulse(this.initState, this.state, this.props.store)) {
        alert("Something went wrong");
      }
      this.props.navigator.pop();
    } else {
      alert("Something is not right!")
    }
  }

  render() {
    return(
      <View>
        <Text>EDIT PULSE ENTRY</Text>
          <View>
            <Text>Pulse value:  </Text>
            <TextInput value={this.state.pulse}
               onFocus={() => this.setState({pulse: ''})}
               onChangeText={(pulse) => this.setState({pulse})}/>
             <Text>Feeling:  </Text>
             <TextInput value={this.state.feeling}
                  onFocus={() => this.setState({feeling: ''})}
                  onChangeText={(feeling) => this.setState({feeling})}/>
          </View>
          <View>
            <CustomButton name='RETURN' onPress={() => this.props.navigator.pop()}/>
            <CustomButton name='OK' onPress={() => this.addItem()}/>
          </View>
        </View>
    );
  }
}
