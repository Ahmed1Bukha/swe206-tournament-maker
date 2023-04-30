import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/src/widgets/placeholder.dart';
import 'package:swe206/UI_componenets/tournament_card_admin.dart';

class StudentCardAdmin extends StatelessWidget {
  const StudentCardAdmin(
      this.studentId, this.studentName, this.teams, this.tournaments,
      {super.key});
  final int studentId;
  final String studentName;
  final List<String> teams;
  final List<TournamentCardAdmin> tournaments;
  @override
  Widget build(BuildContext context) {
    return const Placeholder();
  }
}
