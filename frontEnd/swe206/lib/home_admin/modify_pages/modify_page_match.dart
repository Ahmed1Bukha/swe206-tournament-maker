import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/match_card_admin.dart';

class ModifyMatchPage extends StatefulWidget {
  const ModifyMatchPage(this.matchInfo, {super.key});
  final MatchCardAdmin matchInfo;

  @override
  State<ModifyMatchPage> createState() => _ModifyMatchPageState();
}

class _ModifyMatchPageState extends State<ModifyMatchPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Modify Match"),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text(
            "Enter Score:",
            style: TextStyle(fontSize: 20),
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
                      widget.matchInfo.participantA,
                    ),
                  ),
                  SizedBox(
                    child: TextField(),
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
                    widget.matchInfo.participantB,
                  ),
                  SizedBox(
                    child: TextField(),
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
              onPressed: () {
                Navigator.pop(context);
              },
              child: Text("Submit"))
        ],
      ),
    );
  }
}
