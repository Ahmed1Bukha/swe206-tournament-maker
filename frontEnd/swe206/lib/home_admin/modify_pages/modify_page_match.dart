import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/const.dart';
import 'package:swe206/UI_componenets/match_card_admin.dart';
import 'package:swe206/requests.dart';

class ModifyMatchPage extends StatefulWidget {
  const ModifyMatchPage(this.matchInfo, {super.key});
  final MatchCardAdmin matchInfo;

  @override
  State<ModifyMatchPage> createState() => _ModifyMatchPageState();
}

class _ModifyMatchPageState extends State<ModifyMatchPage> {
  TextEditingController myPartcipantA = new TextEditingController();
  TextEditingController myPartcipantB = new TextEditingController();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        iconTheme: IconThemeData(color: Colors.black),
        title: Text(
          "Modify Match",
          style: h2,
        ),
      ),
      body: Column(
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
                      widget.matchInfo.participantA,
                      style: h4,
                    ),
                  ),
                  SizedBox(
                    child: TextField(
                      controller: myPartcipantA,
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
                    widget.matchInfo.participantB,
                    style: h4,
                  ),
                  SizedBox(
                    child: TextField(
                      controller: myPartcipantB,
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
                await Requests.enterResult(
                    widget.matchInfo.id,
                    int.parse(myPartcipantA.text),
                    int.parse(myPartcipantB.text));
                Navigator.pop(context);
                SnackBar(content: Text("Done Entering"));
              },
              child: const Text("Submit"))
        ],
      ),
    );
  }
}
