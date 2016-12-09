import React, { Component } from 'react';
import {
  DatePickerAndroid,
  StyleSheet,
  Text,
  TouchableWithoutFeedback,
} from 'react-native';

var UIExplorerBlock = require('./UIExplorerBlock');
var UIExplorerPage = require('./UIExplorerPage');

class DatePickerAndroidExample extends React.Component {
  static title = 'DatePickerAndroid';
  static description = 'Standard Android date picker dialog';

  state = {
    presetDate: new Date(2020, 4, 5),
    allDate: new Date(2020, 4, 5),
    simpleText: 'pick a date',
    minText: 'pick a date, no earlier than today',
    maxText: 'pick a date, no later than today',
    presetText: 'pick a date, preset to 2020/5/5',
    allText: 'pick a date between 2020/5/1 and 2020/5/10',
  };

  showPicker = async (stateKey, options) => {
    try {
      var newState = {};
      const {action, year, month, day} = await DatePickerAndroid.open(options);
      if (action === DatePickerAndroid.dismissedAction) {
        newState[stateKey + 'Text'] = 'dismissed';
      } else {
        var date = new Date(year, month, day);
        newState[stateKey + 'Text'] = date.toLocaleDateString();
        newState[stateKey + 'Date'] = date;
      }
      this.setState(newState);
    } catch ({code, message}) {
      console.warn(`Error in example '${stateKey}': `, message);
    }
  };

  render() {
    return (
      <UIExplorerPage title="DatePickerAndroid">
        <UIExplorerBlock title="Simple date picker">
          <TouchableWithoutFeedback
            onPress={this.showPicker.bind(this, 'simple', {date: this.state.simpleDate})}>
            <Text style={styles.text}>{this.state.simpleText}</Text>
          </TouchableWithoutFeedback>
        </UIExplorerBlock>
        <UIExplorerBlock title="Date picker with pre-set date">
          <TouchableWithoutFeedback
            onPress={this.showPicker.bind(this, 'preset', {date: this.state.presetDate})}>
            <Text style={styles.text}>{this.state.presetText}</Text>
          </TouchableWithoutFeedback>
        </UIExplorerBlock>
        <UIExplorerBlock title="Date picker with minDate">
          <TouchableWithoutFeedback
            onPress={this.showPicker.bind(this, 'min', {
              date: this.state.minDate,
              minDate: new Date(),
            })}>
            <Text style={styles.text}>{this.state.minText}</Text>
          </TouchableWithoutFeedback>
        </UIExplorerBlock>
        <UIExplorerBlock title="Date picker with maxDate">
          <TouchableWithoutFeedback
            onPress={this.showPicker.bind(this, 'max', {
              date: this.state.maxDate,
              maxDate: new Date(),
            })}>
            <Text style={styles.text}>{this.state.maxText}</Text>
          </TouchableWithoutFeedback>
        </UIExplorerBlock>
        <UIExplorerBlock title="Date picker with all options">
          <TouchableWithoutFeedback
            onPress={this.showPicker.bind(this, 'all', {
              date: this.state.allDate,
              minDate: new Date(2020, 4, 1),
              maxDate: new Date(2020, 4, 10),
            })}>
            <Text style={styles.text}>{this.state.allText}</Text>
          </TouchableWithoutFeedback>
          </UIExplorerBlock>
      </UIExplorerPage>
    );
  }
}

var styles = StyleSheet.create({
  text: {
    color: 'black',
  },
});
