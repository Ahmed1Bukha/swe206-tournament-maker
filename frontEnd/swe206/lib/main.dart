import 'package:flutter/material.dart';
import 'package:swe206/authintication/auth.dart';
import 'package:swe206/home_admin/home_page_admin.dart';
import 'package:swe206/home_student/home_page_student.dart';
import 'package:swe206/home_student/tournament_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'homeManager',
      theme: ThemeData(),
      initialRoute: AuthPage.id,
      routes: {
        AuthPage.id: (context) => const AuthPage(),
        HomePageAdmin.id: (context) => const HomePageAdmin(),
        HomePageStudent.id: (context) => const HomePageStudent(),
        TournamentPage.id: (context) => const TournamentPage()
      },
    );
  }
}
