import 'package:flutter/material.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:swe206/authintication/auth_page_admin.dart';
import 'package:swe206/authintication/auth_page_student.dart';

import '../UI_componenets/const.dart';

class WelcomePage extends StatelessWidget {
  const WelcomePage({super.key});
  static String id = 'WelcomePage';
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Center(
                child: Text(
                  style: h1,
                  "Tournament Maker",
                  textAlign: TextAlign.center,
                ),
              ),
              SizedBox(
                height: 70,
              ),
              Center(
                child: SvgPicture.asset(
                  "lib/assets/svg/Group.svg",
                  width: MediaQuery.of(context).size.width - 150,
                ),
              ),
              SizedBox(
                height: 90,
              ),
              ElevatedButton.icon(
                icon: Icon(Icons.book),
                style: TextButton.styleFrom(
                  elevation: 4,
                  primary: Colors.white,
                  backgroundColor: Colors.blueAccent,
                  shape: const BeveledRectangleBorder(
                      borderRadius: BorderRadius.all(Radius.circular(5))),
                ),
                onPressed: () {
                  Navigator.pushNamed(context, AuthPageStudent.id);
                },
                label: const Text(
                  "Student Page",
                  style: TextStyle(
                      fontFamily: "Mplus",
                      fontWeight: FontWeight.w700,
                      fontSize: 30),
                ),
              ),
              ElevatedButton.icon(
                icon: Icon(Icons.admin_panel_settings),
                style: TextButton.styleFrom(
                  elevation: 4,
                  primary: Colors.white,
                  backgroundColor: Colors.blueAccent,
                  shape: const BeveledRectangleBorder(
                      borderRadius: BorderRadius.all(Radius.circular(5))),
                ),
                onPressed: () {
                  Navigator.pushNamed(context, AuthPageAdmin.id);
                },
                label: const Text(
                  "Admin Page",
                  style: TextStyle(
                    fontFamily: "Mplus",
                    fontWeight: FontWeight.w700,
                    fontSize: 30,
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
