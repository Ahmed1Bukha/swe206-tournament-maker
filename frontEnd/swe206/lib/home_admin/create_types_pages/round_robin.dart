// ignore_for_file: avoid_print

import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:syncfusion_flutter_datepicker/datepicker.dart';

import '../../requests.dart';

enum BasedTeam { INDIVIDUAL, TEAM_BASED }

class CreateRoundPage extends StatefulWidget {
  const CreateRoundPage({super.key});

  @override
  State<CreateRoundPage> createState() => _CreateRoundPageState();
}

class _CreateRoundPageState extends State<CreateRoundPage> {
  BasedTeam? _character = BasedTeam.INDIVIDUAL;
  dynamic fromUntilDate;
  dynamic toDate;
  String dropdownvalue = 'football';
  int numberOfMembers = 1;
  bool isTeam = false;
  var games = ["football"];
  bool isLoading = true;
  final myTournamentController = TextEditingController();
  final myNumberParticipantController = TextEditingController();
  final myNumberDaysController = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  getGames() async {
    setState(() {
      isLoading = true;
    });
    games = await Requests.getGames();
    setState(() {
      isLoading = false;
    });
  }

  @override
  void initState() {
    getGames();
    super.initState();
  }

  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Create Round robin tournament"),
      ),
      body: isLoading
          ? CircularProgressIndicator()
          : Padding(
              padding: const EdgeInsets.all(16.0),
              child: Form(
                key: _formKey,
                child: SingleChildScrollView(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        "Tournament Name: ",
                        style: TextStyle(fontSize: 20),
                      ),
                      TextFormField(
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Please enter some text';
                          }
                          return null;
                        },
                        controller: myTournamentController,
                        decoration: const InputDecoration(
                          hintText: "Enter tournament name",
                        ),
                      ),
                      const SizedBox(
                        height: 40,
                      ),
                      const Text(
                        "Pick a game:",
                        style: TextStyle(fontSize: 20),
                      ),
                      Center(
                        child: getDropDownButton(),
                      ),
                      Center(
                        child: ElevatedButton(
                          onPressed: () => _showMyDialog(),
                          child: const Text("Add a game"),
                        ),
                      ),
                      const SizedBox(
                        height: 40,
                      ),
                      const Text(
                        "Pick type of participants: ",
                        style: TextStyle(fontSize: 20),
                      ),
                      listTiles(),
                      const SizedBox(
                        height: 40,
                      ),
                      const Text(
                        "Enter number of participant:",
                        style: TextStyle(fontSize: 20),
                      ),
                      TextFormField(
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Please enter some number';
                          }
                          return null;
                        },
                        keyboardType: TextInputType.number,
                        controller: myNumberParticipantController,
                        decoration: const InputDecoration(
                          hintText: "Enter number",
                        ),
                      ),
                      Visibility(
                        visible: isTeam,
                        child: Column(
                          children: [
                            const Text(
                              "Enter number of members in team: ",
                              style: TextStyle(fontSize: 20),
                            ),
                            TextFormField(
                              onChanged: (value) {
                                numberOfMembers = int.parse(value);
                              },
                              validator: (value) {
                                if (value == null || value.isEmpty) {
                                  return 'Please enter some number';
                                }
                                return null;
                              },
                              keyboardType: TextInputType.number,
                              decoration: const InputDecoration(
                                hintText: "Enter number",
                              ),
                            ),
                          ],
                        ),
                      ),
                      const SizedBox(
                        height: 40,
                      ),
                      const Text(
                        "Enter number of days between:",
                        style: TextStyle(fontSize: 20),
                      ),
                      TextFormField(
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Please enter some number';
                          }
                          return null;
                        },
                        keyboardType: TextInputType.number,
                        controller: myNumberDaysController,
                        decoration: const InputDecoration(
                          hintText: "Enter number",
                        ),
                      ),
                      const SizedBox(
                        height: 70,
                      ),
                      const Text(
                        "Select date range:",
                        style: TextStyle(fontSize: 20),
                      ),
                      SfDateRangePicker(
                        selectionMode: DateRangePickerSelectionMode.range,
                        onSelectionChanged: _onSelectionChangedFrom,
                      ),
                      Center(
                        child: ElevatedButton(
                          onPressed: () async {
                            //TODO connect to backend to add the stuff.
                            if (_formKey.currentState!.validate()) {
                              // If the form is valid, display a snackbar. In the real world,
                              // you'd often call a server or save the information in a database.
                              print(myTournamentController.text);
                              print(dropdownvalue);
                              print(myNumberParticipantController.text);
                              ScaffoldMessenger.of(context).showSnackBar(
                                // we can use this as await.
                                const SnackBar(
                                    content: Text('Processing Data')),
                              );

                              var res = await Requests.addRoundRobin(
                                  myTournamentController.text,
                                  int.parse(myNumberParticipantController.text),
                                  fromUntilDate.startDate
                                      .toString()
                                      .split(" ")[0],
                                  fromUntilDate.endDate
                                      .toString()
                                      .split(" ")[0],
                                  int.parse(myNumberDaysController.text),
                                  _character.toString().split(".")[1],
                                  numberOfMembers,
                                  dropdownvalue);
                              print(res);

                              Navigator.pop(context);
                            }
                          },
                          child: const Text(
                            "Submit",
                            style: TextStyle(fontSize: 30),
                          ),
                        ),
                      )
                    ],
                  ),
                ),
              ),
            ),
    );
  }

  void _onSelectionChangedFrom(DateRangePickerSelectionChangedArgs args) {
    fromUntilDate = args.value;
    print(fromUntilDate);
  }

  Column listTiles() {
    return Column(
      children: <Widget>[
        ListTile(
          title: const Text('single'),
          leading: Radio<BasedTeam>(
            value: BasedTeam.INDIVIDUAL,
            groupValue: _character,
            onChanged: (BasedTeam? value) {
              setState(() {
                _character = value;
                isTeam = false;
              });
            },
          ),
        ),
        ListTile(
          title: const Text('team'),
          leading: Radio<BasedTeam>(
            value: BasedTeam.TEAM_BASED,
            groupValue: _character,
            onChanged: (BasedTeam? value) {
              setState(() {
                _character = value;
                isTeam = true;
              });
            },
          ),
        ),
      ],
    );
  }

  DropdownButton<String> getDropDownButton() {
    return DropdownButton(
      // Initial Value
      value: dropdownvalue,
      hint: const Text("Pick a game"),

      // Down Arrow Icon
      icon: const Icon(Icons.keyboard_arrow_down),

      // Array list of items
      items: games.map((String items) {
        return DropdownMenuItem(
          value: items,
          child: Text(items),
        );
      }).toList(),
      // After selecting the desired option,it will
      // change button value to selected value
      onChanged: (String? newValue) {
        setState(() {
          dropdownvalue = newValue!;
        });
      },
    );
  }

  Future<void> _showMyDialog() async {
    final _formKey = GlobalKey<FormState>();
    final gameNameController = TextEditingController();
    return showDialog<void>(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Add game'),
          content: SingleChildScrollView(
            child: Form(
              key: _formKey,
              child: ListBody(
                children: <Widget>[
                  const Text('Enter game name:'),
                  TextFormField(
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please enter some text';
                      }
                      return null;
                    },
                    controller: gameNameController,
                  ),
                ],
              ),
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Add'),
              onPressed: () async {
                if (_formKey.currentState!.validate()) {
                  games.add(gameNameController.text);
                  await Requests.addGames(gameNameController.text);
                  Navigator.of(context).pop();
                  setState(() {});
                }
              },
            ),
            TextButton(
              child: const Text('Cancel'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}
