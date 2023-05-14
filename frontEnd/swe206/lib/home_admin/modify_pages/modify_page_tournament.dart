import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';

import '../../UI_componenets/const.dart';
import '../../requests.dart';

class ModifyTournamentPage extends StatefulWidget {
  const ModifyTournamentPage(this.tournamentCard, {super.key});
  final TournamentCardAdmin tournamentCard;
  @override
  State<ModifyTournamentPage> createState() => _ModifyTournamentPageState();
}

class _ModifyTournamentPageState extends State<ModifyTournamentPage> {
  final myTournamentControllerA = TextEditingController();
  final myTournamentControllerB = TextEditingController();
  var games = ["football"];
  String dropdownvalue = "football";
  bool isLoading = false;
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
    dropdownvalue = widget.tournamentCard.game;

    getGames();

    super.initState();
  }

  Widget build(BuildContext context) {
    final _formKey = GlobalKey<FormState>();
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        iconTheme: IconThemeData(color: Colors.black),
        title: Text(
          "Enter result",
          style: h2,
        ),
      ),
      body: isLoading
          ? CircularProgressIndicator()
          : Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text(
                  "Enter Score:",
                  style: h3,
                ),
                SizedBox(
                  height: 10,
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Center(
                          child: Text(
                            "A",
                            style: h4,
                          ),
                        ),
                        SizedBox(
                          child: TextField(
                            controller: myTournamentControllerA,
                          ),
                          width: 50,
                        )
                      ],
                    ),
                    SizedBox(
                      width: 30,
                    ),
                    Column(
                      children: [
                        Text(
                          "B",
                          style: h4,
                        ),
                        SizedBox(
                          child: TextField(
                            controller: myTournamentControllerB,
                          ),
                          width: 50,
                        )
                      ],
                    )
                  ],
                ),
                SizedBox(
                  height: 50,
                ),
                ElevatedButton(
                    onPressed: () async {
                      var res = await Requests.enterResult(
                          widget.tournamentCard.id,
                          int.parse(myTournamentControllerA.text),
                          int.parse(myTournamentControllerB.text));
                      print(res);
                      // Navigator.pop(context);
                    },
                    child: const Text("Submit"))
              ],
            ),
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
