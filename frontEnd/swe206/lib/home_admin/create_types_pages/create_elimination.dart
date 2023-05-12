// ignore_for_file: avoid_print

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/requests.dart';
import 'package:syncfusion_flutter_datepicker/datepicker.dart';

import '../../UI_componenets/const.dart';

enum BasedTeam { INDIVIDUAL, TEAM_BASED }

class CreateEleminationPage extends StatefulWidget {
  const CreateEleminationPage({super.key});

  @override
  State<CreateEleminationPage> createState() => _CreateEleminationPageState();
}

class _CreateEleminationPageState extends State<CreateEleminationPage> {
  BasedTeam? _character = BasedTeam.INDIVIDUAL;
  dynamic fromUntilDate;
  dynamic toDate;
  String dropdownvalue = "football";
  int numberOfMembers = 1;
  bool isTeam = false;
  var games = ["football"];

  final myTournamentController = TextEditingController();
  final myNumberParticipantController = TextEditingController();
  final myNumberDaysController = TextEditingController();
  final _formKey = GlobalKey<FormState>();

  bool isLoading = true;

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
        iconTheme: IconThemeData(color: Colors.black),
        backgroundColor: Colors.white,
        elevation: 0,
        title: Text(
          "Elimination",
          style: h2,
        ),
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
                      TextFormField(
                        validator: (value) {
                          if (value == null || value.isEmpty) {
                            return 'Please enter some text';
                          }
                          return null;
                        },
                        controller: myTournamentController,
                        decoration: InputDecoration(
                          prefixIcon: Icon(Icons.gamepad),
                          hintText: "Enter Tournament name",
                          filled: true,
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(20),
                            borderSide: BorderSide.none,
                          ),
                        ),
                      ),
                      const SizedBox(
                        height: 40,
                      ),
                      Text(
                        "Tournament game:",
                        style: h4,
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
                      Text(
                        "Tournament based:",
                        style: h4,
                      ),
                      listTiles(),
                      const SizedBox(
                        height: 40,
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
                        decoration: InputDecoration(
                          prefixIcon: Icon(Icons.people),
                          hintText: "Number of participant/teams",
                          filled: true,
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(20),
                            borderSide: BorderSide.none,
                          ),
                        ),
                      ),
                      const SizedBox(
                        height: 40,
                      ),
                      Visibility(
                        visible: isTeam,
                        child: Column(
                          children: [
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
                              decoration: InputDecoration(
                                prefixIcon: Icon(Icons.handshake),
                                hintText: "Number of members per team",
                                filled: true,
                                border: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(20),
                                  borderSide: BorderSide.none,
                                ),
                              ),
                            ),
                          ],
                        ),
                      ),
                      const SizedBox(
                        height: 40,
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
                        decoration: InputDecoration(
                          prefixIcon: Icon(Icons.calendar_month),
                          hintText: "Days between",
                          filled: true,
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(20),
                            borderSide: BorderSide.none,
                          ),
                        ),
                      ),
                      const SizedBox(
                        height: 70,
                      ),
                      Text(
                        "Select date range:",
                        style: h4,
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
                              ScaffoldMessenger.of(context).showSnackBar(
                                // we can use this as await.
                                const SnackBar(
                                    content: Text('Processing Data')),
                              );
                              var res = await Requests.addElemeniation(
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
                              ScaffoldMessenger.of(context).clearSnackBars();
                              if (res == "done") {
                                ScaffoldMessenger.of(context).showSnackBar(
                                  // we can use this as await.
                                  const SnackBar(
                                      content:
                                          Text('Done adding the tournament.')),
                                );
                              } else {
                                ScaffoldMessenger.of(context).showSnackBar(
                                  // we can use this as await.
                                  SnackBar(content: Text(res.toString())),
                                );
                              }
                              // If the form is valid, display a snackbar. In the real world,
                              // you'd often call a server or save the information in a database.

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
          title: Text(
            'INDIVIDUAL',
            style: h5,
          ),
          leading: Radio<BasedTeam>(
            value: BasedTeam.INDIVIDUAL,
            groupValue: _character,
            onChanged: (BasedTeam? value) {
              print(value);
              setState(() {
                _character = value;
                isTeam = false;
              });
            },
          ),
        ),
        ListTile(
          title: Text(
            'TEAM_BASED',
            style: h5,
          ),
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
