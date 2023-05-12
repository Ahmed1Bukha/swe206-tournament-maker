import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:swe206/UI_componenets/const.dart';

import '../UI_componenets/student_card_admin.dart';

class StudentPageAdmin extends StatelessWidget {
  StudentPageAdmin(this.studentInfo, {super.key});
  final StudentCardAdmin studentInfo;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        iconTheme: const IconThemeData(color: Colors.black),
        elevation: 0,
        title: Text(
          "Student Info",
          style: h2,
        ),
      ),
      body: SafeArea(
          child: Padding(
        padding: const EdgeInsets.all(20.0),
        child: SingleChildScrollView(
          child: Column(
            children: [
              Center(
                child: Text(
                  studentInfo.studentName,
                  style: h2,
                ),
              ),
              SizedBox(
                width: MediaQuery.of(context).size.width - 100,
                child: SvgPicture.asset("lib/assets/svg/student-login.svg"),
              ),
              Text(
                "Student ID: ${studentInfo.studentId}",
                style: h4,
              ),
              Center(
                child: Text(
                  "Tournaments: ${studentInfo.tournaments}",
                  style: h4,
                ),
              ),
              Text(
                "Teams: ${studentInfo.teams}",
                style: h4,
              ),
            ],
          ),
        ),
      )),
    );
  }
}
