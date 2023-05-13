import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:flutter_svg/svg.dart';
import 'package:swe206/UI_componenets/const.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';
import 'package:swe206/home_admin/student_page_admin.dart';

class StudentCardAdmin extends StatelessWidget {
  const StudentCardAdmin(
      this.studentId, this.studentName, this.teams, this.tournaments,
      {super.key});
  final int studentId;
  final String studentName;
  final List<dynamic> teams;
  final List<dynamic> tournaments;
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => StudentPageAdmin(this),
          ),
        );
      },
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: PhysicalModel(
            color: Colors.white,
            elevation: 20,
            child: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: SizedBox(
                    width: MediaQuery.of(context).size.width - 20,
                    height: 80,
                    child: SvgPicture.asset(
                      "lib/assets/svg/student-login.svg",
                    ),
                  ),
                ),
                SizedBox(
                  height: 7,
                ),
                Text(
                  "Student:${studentName}",
                  style: h4,
                )
              ],
            )),
      ),
    );
  }
}
