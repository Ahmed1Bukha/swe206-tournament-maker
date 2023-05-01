import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';

class ModifyTournamentPage extends StatefulWidget {
  const ModifyTournamentPage(this.tournamentCard, {super.key});
  final TournamentCardAdmin tournamentCard;
  @override
  State<ModifyTournamentPage> createState() => _ModifyTournamentPageState();
}

class _ModifyTournamentPageState extends State<ModifyTournamentPage> {
  final myTournamentController = TextEditingController();
  var games = [
    'VolleyBall',
    'FootBall',
    'Boxing',
    'BasketBall',
  ];
  String dropdownvalue = "";

  @override
  void initState() {
    myTournamentController.text = widget.tournamentCard.title;
    dropdownvalue = widget.tournamentCard.game;
    super.initState();
  }

  Widget build(BuildContext context) {
    final _formKey = GlobalKey<FormState>();
    return Scaffold(
      appBar: AppBar(
        title: Text("Modify tournament"),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Text(
                "Tournament title:",
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
                "Tournament game:",
                style: TextStyle(fontSize: 20),
              ),
              Center(
                child: getDropDownButton(),
              ),
              const SizedBox(
                height: 50,
              ),
              Center(
                child: ElevatedButton(
                  onPressed: () {
                    //TODO connect to backend to add the stuff.
                    if (_formKey.currentState!.validate()) {
                      // If the form is valid, display a snackbar. In the real world,
                      // you'd often call a server or save the information in a database.
                      print(myTournamentController.text);
                      print(dropdownvalue);

                      ScaffoldMessenger.of(context).showSnackBar(
                        // we can use this as await.
                        const SnackBar(content: Text('Processing Data')),
                      );
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
}
