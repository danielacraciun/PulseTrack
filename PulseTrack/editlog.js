import React, { Component } from 'react';
import {
  Text,
  TextInput,
  Navigator,
  View
} from 'react-native';
import { CustomButton } from './button.js'
import realm from './models';

export class EditItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pulse: props.item.value.toString(),
      feeling: props.item.feeling,
      object : props.item
    };
    this.initState = this.state;
  }

  replacePulse(item, newItem) {
    realm.write(() => {
    realm.create('Pulse', {id: item.object.id,
                value: parseInt(newItem.pulse), feeling: newItem.feeling,
                loggedAt:item.object.loggedAt}, true);
    });
    return true;
  }

  addItem() {
    if (this.state.pulse != ''&& this.state.feling != '' && this.state.pulse == parseInt(this.state.pulse, 10))
    {
      if (!this.replacePulse(this.initState, this.state)) {
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
