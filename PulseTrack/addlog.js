import React, { Component } from 'react';
import {
  Text,
  TextInput,
  Navigator,
  View,
} from 'react-native';
import { CustomButton } from './button.js'
import realm from './models';
import { Form, InputField,
        Separator, SwitchField, LinkField ,
        PickerField, DatePickerField
      } from 'react-native-form-generator';

export class AddItem extends Component {
  constructor(props) {
    super(props);
    this.state = {
      pulse: '',
      feeling: '',
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
      alert(this.state.loggedAt);
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

  handleFormChange(formData){
    this.state.pulse = formData.pulse;
    this.state.feeling = formData.feeling;
    this.state.loggedAt = formData.loggedAt;
   }

  render() {
    return(
      <View>
        <Text>LOG PULSE</Text>
          <Form ref='pulseForm'
            onChange={this.handleFormChange.bind(this)}
            label="Pulse Information">
            <InputField ref='pulse' placeholder='Enter pulse'/>
            <InputField ref='feeling' placeholder='How are you feeling?'/>
            <DatePickerField ref='loggedAt'
              minimumDate={new Date('1/1/1900')}
              maximumDate={new Date()} mode='date' placeholder='Taken at'/>
          </Form>
          <CustomButton name='RETURN' onPress={() => this.props.navigator.pop()}/>
          <CustomButton name='SUBMIT LOG' onPress={() => this.addItem()}/>
      </View>
    );
  }
}
